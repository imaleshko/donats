import { api } from "@/app/api.ts";

export interface Update {
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
  getUpdates: async (fundraiserId: number): Promise<Update[]> => {
    const response = await api.get<Update[]>(
      `/fundraisers/${fundraiserId}/updates`,
    );
    return response.data;
  },

  getSuccessfulDonations: async (fundraiserId: number): Promise<Donation[]> => {
    const response = await api.get<Donation[]>(
      `/fundraisers/${fundraiserId}/donations`,
    );
    return response.data;
  },

  deleteUpdate: async (
    fundraiserId: number,
    updateId: number,
  ): Promise<void> => {
    await api.delete(`/fundraisers/${fundraiserId}/updates/${updateId}`);
  },
};
