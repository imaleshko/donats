import styles from "./Fundraisers.module.css";
import { useNavigate } from "react-router";
import FundraiserCard from "./FundraiserCard/FundraiserCard.tsx";
import { useFundraisers } from "./useFundraisers.ts";
import CloseFundraiserModal from "./CloseFundraiserModal/CloseFundraiserModal.tsx";
import { useState } from "react";
import { useCloseFundraiser } from "./useCloseFundraiser.ts";

const Fundraisers = () => {
  const navigate = useNavigate();

  const { data: fundraisers, isLoading } = useFundraisers();
  const {
    closeFundraiser,
    isPending,
    error: closeError,
  } = useCloseFundraiser();

  const [closingFundraiserId, setClosingFundraiserId] = useState<number | null>(
    null,
  );

  const handleConfirmClose = () => {
    if (closingFundraiserId === null) return;

    closeFundraiser(closingFundraiserId, {
      onSuccess: () => {
        setClosingFundraiserId(null);
      },
    });
  };

  const isEmpty = !fundraisers || fundraisers.length === 0;

  return (
    <div className={styles.wrapper}>
      <div className={styles.header}>
        <h2 className={styles.title}>Мої збори</h2>
        <button
          className={styles.createButton}
          onClick={() => navigate("create")}
        >
          Додати новий збір
        </button>
      </div>

      <div className={styles.divider} />

      {closeError && <p className={styles.errorText}>{closeError}</p>}

      {isLoading ? (
        <p className={styles.empty}>Завантаження...</p>
      ) : isEmpty ? (
        <p className={styles.empty}>Ви ще не створили жодного збору</p>
      ) : (
        <div className={styles.cardsList}>
          {fundraisers.map((fundraiser) => (
            <FundraiserCard
              key={fundraiser.id}
              fundraiser={fundraiser}
              onAddUpdate={(id) => navigate(`update/${id}`)}
              onEdit={(id) => navigate(`edit/${id}`)}
              onClose={setClosingFundraiserId}
            />
          ))}
        </div>
      )}
      <CloseFundraiserModal
        isOpen={closingFundraiserId !== null}
        isPending={isPending}
        onClose={() => setClosingFundraiserId(null)}
        onConfirm={handleConfirmClose}
      />
    </div>
  );
};

export default Fundraisers;
