import { useInfiniteQuery } from "@tanstack/react-query";
import { homeApi } from "./homeApi.ts";

const useFundraisers = (tag: string | null) => {
  return useInfiniteQuery({
    queryKey: ["fundraisers", tag],
    queryFn: ({ pageParam }) => homeApi.getFundraisers(pageParam, tag),
    initialPageParam: 0,
    getNextPageParam: (lastPage, allPages) =>
      lastPage.hasMore ? allPages.length : undefined,
  });
};

export default useFundraisers;
