import { useQuery } from "@tanstack/react-query";
import { homeApi } from "../Home/homeApi.ts";
import { fundraiserPageApi } from "./fundraiserPageApi.ts";

export const useFundraiserPage = (username: string, slug: string) => {
  const fundraiserQuery = useQuery({
    queryKey: ["fundraiser", username, slug],
    queryFn: () => homeApi.getByUsernameAndSlug(username, slug),
  });

  const fundraiserId = fundraiserQuery.data?.id;

  const updatesQuery = useQuery({
    queryKey: ["fundraiser-updates", fundraiserId],
    queryFn: () => fundraiserPageApi.getUpdates(fundraiserId!),
    enabled: fundraiserId != null,
  });

  const donationsQuery = useQuery({
    queryKey: ["fundraiser-donations", fundraiserId],
    queryFn: () => fundraiserPageApi.getSuccessfulDonations(fundraiserId!),
    enabled: fundraiserId != null,
  });

  return {
    fundraiser: fundraiserQuery.data,
    updates: updatesQuery.data,
    donations: donationsQuery.data,
  };
};
