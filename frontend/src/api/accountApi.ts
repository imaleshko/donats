import { api, setAccessToken } from "../app/api.ts";

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

export interface UsersFundraisersResponse {
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

  changeEmail: async (email: ChangeEmailRequest): Promise<User> => {
    const response = await api.patch<ChangeEmailResponse>(
      "/account/email",
      email,
    );
    setAccessToken(response.data.accessToken);
    return response.data.user;
  },

  changeUsername: async (username: ChangeUsernameRequest): Promise<User> => {
    const response = await api.patch<User>("/account/username", username);
    return response.data;
  },

  changePassword: async (password: ChangePasswordRequest): Promise<void> => {
    await api.patch<void>("/account/password", password);
  },

  changeAvatar: async (avatar: ChangeAvatarRequest): Promise<User> => {
    const response = await api.patch<User>("/account/avatar", avatar);
    return response.data;
  },

  getMyDonations: async (): Promise<UserDonationResponse[]> => {
    const response =
      await api.get<UserDonationResponse[]>("/account/donations");
    return response.data;
  },

  getUserFundraisers: async (): Promise<UsersFundraisersResponse[]> => {
    const response = await api.get<UsersFundraisersResponse[]>(
      "/account/fundraisers",
    );
    return response.data;
  },
};
