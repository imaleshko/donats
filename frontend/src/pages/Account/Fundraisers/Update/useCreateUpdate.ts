import { useMutation } from "@tanstack/react-query";

import { isAxiosError } from "axios";
import {
  type CreateUpdateRequest,
  fundraiserUpdateApi,
} from "./fundraiserUpdateApi.ts";

export const useCreateUpdate = () => {
  const mutation = useMutation({
    mutationFn: ({
      fundraiserId,
      data,
    }: {
      fundraiserId: number;
      data: CreateUpdateRequest;
    }) => fundraiserUpdateApi.create(fundraiserId, data),
  });

  const getErrorMessage = (): string | null => {
    if (!mutation.error) return null;
    if (isAxiosError(mutation.error)) {
      return (
        mutation.error.response?.data?.detail || "Помилка створення апдейту"
      );
    }
    return mutation.error.message || "Невідома помилка";
  };

  return {
    createUpdate: mutation.mutate,
    isPending: mutation.isPending,
    error: getErrorMessage(),
  };
};
