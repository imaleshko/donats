import { useMutation, useQueryClient } from "@tanstack/react-query";
import { imageApi } from "@/pages/Account/imageApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";
import {
  accountApi,
  type CreateFundraiserRequest,
} from "@/pages/Account/accountApi.ts";

interface CreateFundraiserFormData {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  images: File[];
  tags: string[];
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
        imageUrls: imageUrls,
        tags: data.tags,
      };

      return accountApi.createFundraiser(requestData);
    },
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["fundraiser"] });
    },
  });

  return {
    createFundraiser: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка створення збору"),
  };
};
