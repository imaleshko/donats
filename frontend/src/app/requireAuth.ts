import { queryClient } from "./queryClient.ts";
import { redirect } from "react-router";
import { getUser } from "@/app/getUser.ts";

export const requireAuth = async () => {
  let user;

  try {
    user = await queryClient.ensureQueryData({
      queryKey: ["user"],
      queryFn: getUser,
    });
  } catch {
    throw redirect("/login");
  }

  if (!user) {
    throw redirect("/login");
  }

  return user;
};
