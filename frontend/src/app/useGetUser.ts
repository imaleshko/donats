import { useQuery } from "@tanstack/react-query";
import { getUser } from "@/app/getUser.ts";

const useGetUser = () => {
  return useQuery({
    queryKey: ["user"],
    queryFn: getUser,
    retry: false,
    staleTime: 1000 * 60 * 5,
  });
};

export default useGetUser;
