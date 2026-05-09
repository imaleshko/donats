import { api } from "@/app/api.ts";

export interface EditFundraiserResponse {
  id: number;
  title: string;
  slug: string;
  description: string;
  goal: number | null;
  endDate: string | null;
  existingImagesUrls: string[];
}

export interface UpdateFundraiserRequest {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  endDate?: string;
  imagesUrl: string[];
}

export const fundraiserEditingApi = {
  getForEdit: async (slug: string): Promise<EditFundraiserResponse> => {
    const response = await api.get<EditFundraiserResponse>(
      `/fundraisers/edit/${slug}`,
    );
    return response.data;
  },

  update: async (
    currentSlug: string,
    data: UpdateFundraiserRequest,
  ): Promise<void> => {
    await api.put(`/fundraisers/edit/${currentSlug}`, data);
  },
};
