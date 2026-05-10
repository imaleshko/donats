import { api, setAccessToken } from "@/app/api.ts";

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

export interface User {
  id: number;
  username: string;
  email: string;
  avatarUrl: string | null;
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
  getUser: async (): Promise<User> => {
    const response = await api.get<User>("/account/user");
    return response.data;
  },

  changeEmail: async (data: ChangeEmailRequest): Promise<User> => {
    const response = await api.patch<ChangeEmailResponse>(
      "/account/email",
      data,
    );
    setAccessToken(response.data.accessToken);
    return response.data.user;
  },

  changeUsername: async (data: ChangeUsernameRequest): Promise<User> => {
    const response = await api.patch<User>("/account/username", data);
    return response.data;
  },

  changePassword: async (data: ChangePasswordRequest): Promise<void> => {
    await api.patch<void>("/account/password", data);
  },

  changeAvatar: async (data: ChangeAvatarRequest): Promise<User> => {
    const response = await api.patch<User>("/account/avatar", data);
    return response.data;
  },

  getUserDonations: async (): Promise<UserDonationResponse[]> => {
    const response =
      await api.get<UserDonationResponse[]>("/account/donations");
    return response.data;
  },

  getUserFundraisers: async (): Promise<UserFundraiserResponse[]> => {
    const response = await api.get<UserFundraiserResponse[]>(
      "/account/fundraisers",
    );
    return response.data;
  },
};
