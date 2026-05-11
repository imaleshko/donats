import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  accountApi,
  type ChangeEmailRequest,
} from "@/pages/Account/accountApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

const useChangeEmail = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (data: ChangeEmailRequest) => accountApi.changeEmail(data),
    onSuccess: (updatedUser) => {
      queryClient.setQueryData(["user"], updatedUser);
    },
  });

  return {
    changeEmail: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(
      mutation.error,
      "Помилка зміни електронної адреси",
    ),
  };
};

export default useChangeEmail;
