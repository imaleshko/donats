import styles from "./Fundraisers.module.css";
import { useNavigate } from "react-router";
import FundraiserCard from "./FundraiserCard/FundraiserCard.tsx";
import { useFundraisers } from "@/pages/Account/Fundraisers/useFundraisers.ts";

const Fundraisers = () => {
  const navigate = useNavigate();

  const { data: fundraisers, isLoading } = useFundraisers();

  const handleCreateClick = () => {
    navigate("create");
  };

  const handleAddUpdate = (id: number) => {
    navigate(`update/${id}`);
  };

  const handleEdit = (id: number) => {
    navigate(`edit/${id}`);
  };

  const handleClose = (id: number) => {
    console.log("Закрити збір", id);
  };

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <h2 className={styles.title}>Мої збори</h2>
        <button className={styles.createButton} onClick={handleCreateClick}>
          Додати новий збір
        </button>
      </div>

      <div className={styles.divider} />

      {isLoading ? (
        <p className={styles.empty}>Завантаження...</p>
      ) : !fundraisers || fundraisers.length === 0 ? (
        <p className={styles.empty}>Ви ще не створили жодного збору</p>
      ) : (
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
      )}
    </div>
  );
};

export default Fundraisers;
