import { useQuery } from "@tanstack/react-query";
import { accountApi } from "../pages/Account/accountApi.ts";

const useGetUser = () => {
  return useQuery({
    queryKey: ["user"],
    queryFn: () => accountApi.getUser(),
    retry: false,
    staleTime: 1000 * 60 * 5,
  });
};

export default useGetUser;
