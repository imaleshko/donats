import { useQuery } from "@tanstack/react-query";
import { homeApi } from "./homeApi.ts";

const useNewest = () => {
  return useQuery({
    queryKey: ["newest"],
    queryFn: homeApi.getNewest,
  });
};

export default useNewest;
