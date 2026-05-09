import { useMutation, useQueryClient } from "@tanstack/react-query";
import {
  type CreateFundraiserRequest,
  fundraisersApi,
} from "../api/fundraisersApi.ts";
import { isAxiosError } from "axios";
import { imageApi } from "@/api/imageApi.ts";

interface CreateFundraiserFormData {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  images: File[];
}

export const useCreateFundraiser = () => {
  const queryClient = useQueryClient();

  const mutation = useMutation({
    mutationFn: async (data: CreateFundraiserFormData) => {
      let imageUrls: string[] = [];

      if (data.images.length > 0) {
        imageUrls = await imageApi.uploadImages(data.images);
      }

      const requestData: CreateFundraiserRequest = {
        title: data.title,
        slug: data.slug,
        description: data.description,
        goal: data.goal,
        imagesUrl: imageUrls,
      };

      return fundraisersApi.createFundraiser(requestData);
    },
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["fundraiser"] });
    },
  });

  const getErrorMessage = (): string | null => {
    if (!mutation.error) return null;
    if (isAxiosError(mutation.error)) {
      return mutation.error.response?.data?.detail || "Помилка створення збору";
    }
    return mutation.error.message || "Невідома помилка";
  };

  return {
    createFundraiser: mutation.mutate,
    isPending: mutation.isPending,
    error: getErrorMessage(),
  };
};
