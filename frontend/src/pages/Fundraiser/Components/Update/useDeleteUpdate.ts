import { useMutation, useQueryClient } from "@tanstack/react-query";
import { fundraiserPageApi } from "@/pages/Fundraiser/fundraiserPageApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";

interface DeleteUpdateRequest {
  fundraiserId: number;
  updateId: number;
}

export const useDeleteUpdate = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: ({ fundraiserId, updateId }: DeleteUpdateRequest) =>
      fundraiserPageApi.deleteUpdate(fundraiserId, updateId),
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["fundraiser-updates"] });
    },
  });

  return {
    deleteUpdate: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка видалення оновлення"),
  };
};
