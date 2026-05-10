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

export interface UserFundraiserResponse {
  id: number;
  title: string;
  slug: string;
  username: string;
  startedAt: string;
  status: string;
  balance: number;
  totalDonationsCount: number;
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
    await api.patch<void>("/account/password", data);
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
};
