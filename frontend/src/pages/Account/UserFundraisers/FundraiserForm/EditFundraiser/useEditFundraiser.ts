import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { imageApi } from "@/pages/Account/imageApi.ts";
import { getApiErrorMessage } from "@/utils/getApiErrorMessage.ts";
import {
  accountApi,
  type UpdateFundraiserRequest,
} from "@/pages/Account/accountApi.ts";

export interface EditFundraiserFormData {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  retainedImages: string[];
  newImages: File[];
}

export const useEditFundraiser = (fundraiserId: number) => {
  const queryClient = useQueryClient();

  const query = useQuery({
    queryKey: ["edit-fundraiser", fundraiserId],
    queryFn: () => accountApi.getFundraiserForEdit(fundraiserId),
    enabled: !!fundraiserId,
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
        imageUrls: finalImagesUrl,
      };

      return accountApi.updateFundraiser(fundraiserId, requestData);
    },
    onSuccess: () => {
      void queryClient.invalidateQueries({ queryKey: ["user-fundraisers"] });
      void queryClient.invalidateQueries({ queryKey: ["fundraiser"] });
    },
  });

  return {
    fundraiserData: query.data,
    isLoadingData: query.isLoading,
    updateFundraiser: mutation.mutate,
    isPending: mutation.isPending,
    error: getApiErrorMessage(mutation.error, "Помилка оновлення збору"),
  };
};
