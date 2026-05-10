import { queryClient } from "./queryClient.ts";
import { accountApi } from "../pages/Account/accountApi.ts";
import { redirect } from "react-router";

export const requireAuth = async () => {
  try {
    return await queryClient.ensureQueryData({
      queryKey: ["user"],
      queryFn: accountApi.getUser,
    });
  } catch {
    throw redirect("/login");
  }
};
