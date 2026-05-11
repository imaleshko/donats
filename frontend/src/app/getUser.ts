import { api } from "@/app/api.ts";

export interface User {
  id: number;
  username: string;
  email: string;
  avatarUrl: string | null;
}

export const getUser = async (): Promise<User> => {
  const response = await api.get<User>("/user");
  return response.data;
};
