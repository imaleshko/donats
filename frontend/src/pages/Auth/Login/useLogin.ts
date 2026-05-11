import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router";
import { setAccessToken } from "@/app/api.ts";
import { authApi, type LoginRequest } from "@/pages/Auth/authApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

export const useLogin = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (data: LoginRequest) => authApi.login(data),
    onSuccess: (response) => {
      if (response?.accessToken) {
        setAccessToken(response.accessToken);
      }

      queryClient.removeQueries({ queryKey: ["user"] });
      navigate("/account/profile");
    },
  });

  return {
    login: mutation.mutate,
    isPending: mutation.isPending,
    isError: mutation.isError,
    error: getApiErrorMessage(mutation.error, "Помилка входу"),
  };
};
