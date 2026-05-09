import { api } from "../app/api.ts";

interface FundraiserCard {
  id: number;
  title: string;
  author: string;
  balance: number;
  goal: number;
  slug: string;
}

export interface CreateFundraiserRequest {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  imagesUrl?: string[];
}

export interface Fundraiser {
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
    const response = await api.get<FundraiserCard[]>("/fundraisers/newest");
    return response.data;
  },

  createFundraiser: async (data: CreateFundraiserRequest): Promise<void> => {
    await api.post<void>("/fundraisers/create", data);
  },

  getByUsernameAndSlug: async (
    username: string,
    slug: string,
  ): Promise<Fundraiser> => {
    const response = await api.get<Fundraiser>(
      `/fundraisers/${username}/${slug}`,
    );
    return response.data;
  },
};
