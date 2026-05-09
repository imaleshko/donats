import { useNavigate } from "react-router";
import { useMutation } from "@tanstack/react-query";
import { authApi } from "@/pages/Auth/authApi.ts";
import { setAccessToken } from "@/app/api.ts";
import { queryClient } from "@/app/queryClient.ts";

const useLogout = () => {
  const navigate = useNavigate();

  return useMutation({
    mutationFn: () => authApi.logout(),
    onSettled: () => {
      setAccessToken(null);
      queryClient.removeQueries({ queryKey: ["user"] });
      navigate("/");
    },
  });
};

export default useLogout;
