import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  accountApi,
  type ChangeUsernameRequest,
} from "@/pages/Account/accountApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

const useChangeUsername = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (data: ChangeUsernameRequest) =>
      accountApi.changeUsername(data),
    onSuccess: (updatedUser) => {
      queryClient.setQueryData(["user"], updatedUser);
    },
  });

  return {
    changeUsername: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка зміни імені"),
  };
};

export default useChangeUsername;
