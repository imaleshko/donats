import styles from "./Fundraisers.module.css";
import { useNavigate } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { accountApi } from "@/api/accountApi.ts";
import FundraiserCard from "./FundraiserCard/FundraiserCard.tsx";

const Fundraisers = () => {
  const navigate = useNavigate();

  const { data: fundraisers, isLoading } = useQuery({
    queryKey: ["user-fundraisers"],
    queryFn: accountApi.getUserFundraisers,
  });

  const handleCreateClick = () => {
    navigate("create");
  };

  const handleAddUpdate = (id: number) => {
    navigate(`add-update/${id}`);
  };

  const handleEdit = (slug: string) => {
    navigate(`edit/${slug}`);
  };

  const handleClose = (id: number) => {
    console.log("Закрити збір", id);
  };

  return (
    <div className={styles.wrapper}>
      <section className={styles.section}>
        <div className={styles.header}>
          <button className={styles.createButton} onClick={handleCreateClick}>
            Додати новий збір
          </button>
        </div>

        <div className={styles.divider}></div>

        <div className={styles.content}>
          {isLoading ? (
            <p className={styles.emptyState}>Завантаження...</p>
          ) : fundraisers && fundraisers.length > 0 ? (
            <div className={styles.cardsList}>
              {fundraisers.map((fundraiser) => (
                <FundraiserCard
                  key={fundraiser.id}
                  fundraiser={fundraiser}
                  onAddUpdate={handleAddUpdate}
                  onEdit={handleEdit}
                  onClose={handleClose}
                />
              ))}
            </div>
          ) : (
            <p className={styles.emptyState}>Зборів ще немає</p>
          )}
        </div>
      </section>
    </div>
  );
};

export default Fundraisers;
