import { useQuery } from "@tanstack/react-query";
import { homeApi } from "@/pages/Home/homeApi.ts";

export const useTags = () => {
  return useQuery({
    queryKey: ["tags"],
    queryFn: () => homeApi.getTags(),
  });
};
