import { useNavigate } from "react-router";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { authApi, type RegisterRequest } from "@/pages/Auth/authApi.ts";
import { setAccessToken } from "@/app/api.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

export const useRegister = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (data: RegisterRequest) => authApi.register(data),
    onSuccess: (response) => {
      if (response?.accessToken) {
        setAccessToken(response.accessToken);
      }

      queryClient.removeQueries({ queryKey: ["user"] });
      navigate("/account/profile");
    },
  });

  return {
    register: mutation.mutate,
    isPending: mutation.isPending,
    isError: mutation.isError,
    error: getApiErrorMessage(mutation.error, "Помилка реєстрації"),
  };
};
