import { api, setAccessToken } from "@/app/api.ts";
import type { User } from "@/app/getUser.ts";

export interface ChangeEmailRequest {
  email: string;
}

export interface ChangeEmailResponse {
  user: User;
  accessToken: string;
}

export interface ChangeUsernameRequest {
  username: string;
}

export interface ChangePasswordRequest {
  oldPassword: string;
  newPassword: string;
}

export interface ChangeAvatarRequest {
  avatarUrl: string;
}

export interface UserDonationResponse {
  id: number;
  name: string;
  amount: number;
  createdAt: string;
  message: string | null;
  fundraiserTitle: string;
  fundraiserSlug: string;
  authorUsername: string;
}

export type FundraiserStatus = "ACTIVE" | "CLOSED";

export interface UserFundraiserResponse {
  id: number;
  title: string;
  slug: string;
  username: string;
  startedAt: string;
  closedAt?: string;
  status: FundraiserStatus;
  balance: number;
  totalDonationsCount: number;
}

export interface CreateFundraiserRequest {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  imageUrls: string[];
}

export interface EditFundraiserResponse {
  id: number;
  title: string;
  slug: string;
  description: string;
  goal?: number;
  existingImagesUrls: string[];
}

export interface EditFundraiserRequest {
  title: string;
  slug: string;
  description: string;
  goal?: number;
  imageUrls: string[];
}

export interface CreateUpdateRequest {
  title: string;
  message: string;
}

export const accountApi = {
  changeEmail: async (data: ChangeEmailRequest): Promise<User> => {
    const response = await api.patch<ChangeEmailResponse>(
      "/user/account/email",
      data,
    );
    setAccessToken(response.data.accessToken);
    return response.data.user;
  },

  changeUsername: async (data: ChangeUsernameRequest): Promise<User> => {
    const response = await api.patch<User>("/user/account/username", data);
    return response.data;
  },

  changePassword: async (data: ChangePasswordRequest): Promise<void> => {
    await api.patch<void>("/user/account/password", data);
  },

  changeAvatar: async (data: ChangeAvatarRequest): Promise<User> => {
    const response = await api.patch<User>("/user/account/avatar", data);
    return response.data;
  },

  getUserDonations: async (): Promise<UserDonationResponse[]> => {
    const response = await api.get<UserDonationResponse[]>(
      "/user/account/donations",
    );
    return response.data;
  },

  getUserFundraisers: async (): Promise<UserFundraiserResponse[]> => {
    const response = await api.get<UserFundraiserResponse[]>(
      "/user/account/fundraisers",
    );
    return response.data;
  },

  createFundraiser: async (data: CreateFundraiserRequest): Promise<void> => {
    await api.post<void>("/fundraisers", data);
  },

  getFundraiserForEdit: async (
    fundraiserId: number,
  ): Promise<EditFundraiserResponse> => {
    const response = await api.get<EditFundraiserResponse>(
      `/fundraisers/${fundraiserId}/edit`,
    );
    return response.data;
  },

  editFundraiser: async (
    fundraiserId: number,
    data: EditFundraiserRequest,
  ): Promise<void> => {
    await api.put(`/fundraisers/${fundraiserId}/edit`, data);
  },

  createUpdate: async (
    fundraiserId: number,
    data: CreateUpdateRequest,
  ): Promise<void> => {
    await api.post(`/fundraisers/${fundraiserId}/updates`, data);
  },

  closeFundraiser: async (fundraiserId: number): Promise<void> => {
    await api.put(`/fundraisers/${fundraiserId}/close`);
  },
};
