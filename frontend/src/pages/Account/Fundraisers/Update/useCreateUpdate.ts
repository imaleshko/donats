import { useMutation } from "@tanstack/react-query";

import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";
import {
  accountApi,
  type CreateUpdateRequest,
} from "@/pages/Account/accountApi.ts";

export const useCreateUpdate = () => {
  const mutation = useMutation({
    mutationFn: ({
      fundraiserId,
      data,
    }: {
      fundraiserId: number;
      data: CreateUpdateRequest;
    }) => accountApi.createUpdate(fundraiserId, data),
  });

  return {
    createUpdate: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка створення апдейту"),
  };
};
