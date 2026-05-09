import { api } from "@/app/api.ts";

export interface CreateUpdateRequest {
  title: string;
  message: string;
}

export const fundraiserUpdateApi = {
  create: async (
    fundraiserId: number,
    data: CreateUpdateRequest,
  ): Promise<void> => {
    await api.post(`/fundraisers/${fundraiserId}/updates`, data);
  },
};
