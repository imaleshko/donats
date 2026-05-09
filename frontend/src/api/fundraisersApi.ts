import { api } from "../app/api.ts";

interface FundraiserCard {
  id: number;
  title: string;
  author: string;
  balance: number;
  goal: number;
  slug: string;
}

export interface CreateFundraisingRequest {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  imagesUrl?: string[];
}

export interface GetFundraisingResponse {
  id: number;
  title: string;
  slug: string;
  description: string;
  balance: number;
  goal?: number;
  imageUrls: string[];
  authorUsername: string;
  authorAvatarUrl?: string;
  status: string;
  startedAt: string;
  endedAt: string;
}

export const fundraisersApi = {
  getNewest: async (): Promise<FundraiserCard[]> => {
    const response = await api.get<FundraiserCard[]>("/fundraising/newest");
    return response.data;
  },

  createFundraising: async (data: CreateFundraisingRequest): Promise<void> => {
    await api.post<void>("/fundraising/create", data);
  },

  getByUsernameAndSlug: async (
    username: string,
    slug: string,
  ): Promise<GetFundraisingResponse> => {
    const response = await api.get<GetFundraisingResponse>(
      `/fundraising/${username}/${slug}`,
    );
    return response.data;
  },
};
