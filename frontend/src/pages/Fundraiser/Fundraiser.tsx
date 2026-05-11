import { useMemo } from "react";
import { useParams } from "react-router";
import styles from "./Fundraiser.module.css";
import Carousel from "./Components/Carousel/Carousel.tsx";
import Info from "./Components/Info/Info.tsx";
import DonationForm from "./Components/DonationForm/DonationForm.tsx";
import Description from "./Components/Description/Description.tsx";
import Update from "./Components/Update/Update.tsx";
import DonationsHistory from "./Components/DonationsHistory/DonationsHistory.tsx";
import TopDonations from "./Components/TopDonations/TopDonations.tsx";
import { useFundraiserPage } from "./useFundraiserPage.ts";

export const Fundraiser = () => {
  const { username, slug } = useParams<{ username: string; slug: string }>();

  const { fundraiser, updates, donations } = useFundraiserPage(
    username!,
    slug!,
  );

  const topDonations = useMemo(() => {
    if (!donations) return [];

    return [...donations].sort((a, b) => b.amount - a.amount).slice(0, 5);
  }, [donations]);

  if (!fundraiser) return null;

  return (
    <div className={styles.wrapper}>
      <h1 className={styles.title}>{fundraiser.title}</h1>

      <div className={styles.divider} />

      <div className={styles.topSection}>
        <Carousel imageUrls={fundraiser.imageUrls} />
        <Info fundraiser={fundraiser} />
      </div>

      <DonationForm fundraiserId={fundraiser.id} status={fundraiser.status} />

      <Description description={fundraiser.description} />

      {updates?.map((update) => (
        <Update
          key={update.id}
          title={update.title}
          message={update.message}
          createdAt={update.createdAt}
        />
      ))}

      <div className={styles.bottomSection}>
        <DonationsHistory donations={donations ?? []} />

        <div className={styles.sidebarContainer}>
          <TopDonations donations={topDonations} />
        </div>
      </div>
    </div>
  );
};
