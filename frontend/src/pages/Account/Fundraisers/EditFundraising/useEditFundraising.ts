import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import {
  fundraisingEditingApi,
  type UpdateFundraisingRequest,
} from "@/pages/Account/Fundraisers/EditFundraising/fundraisingEditingApi.ts";
import { imageApi } from "@/api/imageApi.ts";
import { isAxiosError } from "axios";

export interface EditFundraisingFormData {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  endDate?: string;
  retainedImages: string[];
  newImages: File[];
}

export const useEditFundraising = (currentSlug: string) => {
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["edit-fundraiser", currentSlug],
    queryFn: () => fundraisingEditingApi.getForEdit(currentSlug),
    enabled: !!currentSlug,
  });

  const mutation = useMutation({
    mutationFn: async (data: EditFundraisingFormData) => {
      let uploadedUrls: string[] = [];

      if (data.newImages.length > 0) {
        uploadedUrls = await imageApi.uploadImages(data.newImages);
      }

      const finalImagesUrl = [...data.retainedImages, ...uploadedUrls];

      const requestData: UpdateFundraisingRequest = {
        title: data.title,
        slug: data.slug,
        description: data.description,
        goal: data.goal,
        endDate: data.endDate,
        imagesUrl: finalImagesUrl,
      };

      return fundraisingEditingApi.update(currentSlug, requestData);
    },
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["my-fundraisings"] });
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
    fundraisingData: query.data,
    isLoadingData: query.isLoading,
    updateFundraising: mutation.mutate,
    isPending: mutation.isPending,
    error: getErrorMessage(),
  };
};
