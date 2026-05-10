import { useQuery } from "@tanstack/react-query";
import { accountApi } from "@/pages/Account/accountApi.ts";

export const useFundraisers = () => {
  return useQuery({
    queryKey: ["user-fundraisers"],
    queryFn: accountApi.getUserFundraisers,
  });
};
