import { api } from "@/app/api.ts";
import type { FundraiserStatus } from "@/pages/Account/accountApi.ts";

export interface Tag {
  name: string;
}

interface FundraiserCard {
  id: number;
  title: string;
  author: string;
  balance: number;
  goal: number;
  slug: string;
  tags: string[];
}

export interface FundraisersPage {
  fundraisers: FundraiserCard[];
  hasMore: boolean;
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
  getFundraisers: async (
    page: number,
    tag: string | null,
  ): Promise<FundraisersPage> => {
    const response = await api.get<FundraisersPage>("/fundraisers/list", {
      params: { page, tag: tag || undefined },
    });
    return response.data;
  },

  getTags: async (): Promise<Tag[]> => {
    const response = await api.get<Tag[]>("/fundraisers/tags");
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
