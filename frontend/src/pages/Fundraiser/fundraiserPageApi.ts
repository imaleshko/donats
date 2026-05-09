import { api } from "@/app/api.ts";

export interface FundraiserUpdate {
  id: number;
  title: string;
  message: string;
  createdAt: string;
}

export interface Donation {
  id: number;
  name: string;
  amount: number;
  createdAt: string;
  message: string | null;
}

export const fundraiserPageApi = {
  getUpdates: async (id: number): Promise<FundraiserUpdate[]> => {
    const response = await api.get<FundraiserUpdate[]>(
      `/fundraisers/${id}/updates`,
    );
    return response.data;
  },

  getSuccessfulDonations: async (fundraiserId: number): Promise<Donation[]> => {
    const response = await api.get<Donation[]>(
      `/fundraisers/${fundraiserId}/donations`,
    );
    return response.data;
  },
};
