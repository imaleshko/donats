import { useMutation, useQueryClient } from "@tanstack/react-query";
import { accountApi } from "@/pages/Account/accountApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

export const useCloseFundraiser = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: (fundraiserId: number) =>
      accountApi.closeFundraiser(fundraiserId),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["user-fundraisers"] });
    },
  });

  return {
    closeFundraiser: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка закриття збору"),
  };
};
