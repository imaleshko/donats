import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  fundraiserEditingApi,
  type UpdateFundraiserRequest,
} from "./fundraiserEditingApi.ts";
import { imageApi } from "../../imageApi.ts";
import { isAxiosError } from "axios";

export interface EditFundraiserFormData {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  retainedImages: string[];
  newImages: File[];
}

export const useEditFundraiser = (currentSlug: string) => {
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["edit-fundraiser", currentSlug],
    queryFn: () => fundraiserEditingApi.getForEdit(currentSlug),
    enabled: !!currentSlug,
  });

  const mutation = useMutation({
    mutationFn: async (data: EditFundraiserFormData) => {
      let uploadedUrls: string[] = [];

      if (data.newImages.length > 0) {
        uploadedUrls = await imageApi.uploadImages(data.newImages);
      }

      const finalImagesUrl = [...data.retainedImages, ...uploadedUrls];

      const requestData: UpdateFundraiserRequest = {
        title: data.title,
        slug: data.slug,
        description: data.description,
        goal: data.goal,
        imagesUrl: finalImagesUrl,
      };

      return fundraiserEditingApi.update(currentSlug, requestData);
    },
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["user-fundraisers"] });
      void queryClient.invalidateQueries({ queryKey: ["fundraiser"] });
    },
  });

  const getErrorMessage = (): string | null => {
    if (!mutation.error) return null;
    if (isAxiosError(mutation.error)) {
      return mutation.error.response?.data?.detail || "Помилка оновлення збору";
    }
    return mutation.error.message || "Невідома помилка";
  };

  return {
    fundraiserData: query.data,
    isLoadingData: query.isLoading,
    updateFundraiser: mutation.mutate,
    isPending: mutation.isPending,
    error: getErrorMessage(),
  };
};
