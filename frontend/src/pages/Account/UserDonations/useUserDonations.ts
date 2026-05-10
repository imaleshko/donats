import { useQuery } from "@tanstack/react-query";
import { accountApi } from "@/pages/Account/accountApi.ts";

export const useUserDonations = () => {
  return useQuery({
    queryKey: ["user-donations"],
    queryFn: accountApi.getUserDonations,
  });
};
