import { api } from "@/app/api.ts";

export interface DonationInitRequest {
  fundraisingId: number;
  amount: number;
  name: string;
  message?: string;
}

export interface DonationInitResponse {
  data: string;
  signature: string;
}

export const donationApi = {
  initDonation: async (
    data: DonationInitRequest,
  ): Promise<DonationInitResponse> => {
    const response = await api.post<DonationInitResponse>(
      "/donations/init",
      data,
    );
    return response.data;
  },
};
