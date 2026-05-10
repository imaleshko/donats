import styles from "../Form/FundraiserForm.module.css";
import { useNavigate, useParams } from "react-router";
import { useEditFundraiser } from "./useEditFundraiser.ts";
import { type SubmitEvent, useEffect } from "react";
import FundraiserForm from "../Form/FundraiserForm.tsx";
import { useFundraiserFormLogic } from "../Form/useFundraiserFormLogic.ts";

const EditFundraiser = () => {
  const { id } = useParams<{ id: string }>();
  const fundraiserId = Number(id);
  const navigate = useNavigate();

  const {
    fundraiserData,
    isLoadingData,
    updateFundraiser,
    isPending,
    error: updateError,
  } = useEditFundraiser(fundraiserId);
  const formLogic = useFundraiserFormLogic();

  const { setFormData, setRetainedImages, validateForm } = formLogic;

  useEffect(() => {
    if (fundraiserData) {
      setFormData({
        title: fundraiserData.title,
        slug: fundraiserData.slug,
        description: fundraiserData.description,
        goal: fundraiserData.goal ? String(fundraiserData.goal) : "",
      });
      setRetainedImages(fundraiserData.existingImagesUrls);
    }
  }, [fundraiserData, setFormData, setRetainedImages]);

  const handleSubmit = (e: SubmitEvent) => {
    e.preventDefault();

    const data = validateForm();
    if (!data) return;

    updateFundraiser(
      {
        title: data.title,
        slug: data.slug,
        description: data.description,
        goal: data.goal ? Number(data.goal) : undefined,
        retainedImages: formLogic.retainedImages,
        newImages: formLogic.newImages.map((img) => img.file),
      },
      { onSuccess: () => navigate("/account/fundraisers") },
    );
  };

  if (isLoadingData) {
    return (
      <div className={styles.loadingWrapper}>
        <p className={styles.loadingText}>Завантаження даних...</p>
      </div>
    );
  }

  return (
    <FundraiserForm
      title="Редагування збору"
      submitLabel="Зберегти зміни"
      isPending={isPending}
      serverError={updateError}
      originalSlug={fundraiserData?.slug}
      onSubmit={handleSubmit}
      {...formLogic}
    />
  );
};

export default EditFundraiser;
