import { useMutation } from "@tanstack/react-query";
import {
  accountApi,
  type ChangePasswordRequest,
} from "@/pages/Account/accountApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

const useChangePassword = () => {
  const mutation = useMutation({
    mutationFn: (data: ChangePasswordRequest) =>
      accountApi.changePassword(data),
  });

  return {
    changePassword: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка зміни пароля"),
  };
};

export default useChangePassword;
