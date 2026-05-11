import { api } from "@/app/api.ts";
import type { FundraiserStatus } from "@/pages/Account/accountApi.ts";

interface FundraiserCard {
  id: number;
  title: string;
  author: string;
  balance: number;
  goal: number;
  slug: string;
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
  status: FundraiserStatus;
  startedAt: string;
  closedAt?: string;
}

export const homeApi = {
  getNewest: async (): Promise<FundraiserCard[]> => {
    const response = await api.get<FundraiserCard[]>("/fundraisers/newest");
    return response.data;
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
