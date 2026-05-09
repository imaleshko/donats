import styles from "../FundraiserForm/FundraiserForm.module.css";
import { useNavigate, useParams } from "react-router";
import { useEditFundraiser } from "./useEditFundraiser.ts";
import { type SubmitEvent, useEffect } from "react";
import FundraiserForm from "../FundraiserForm/FundraiserForm.tsx";
import { useFundraiserFormLogic } from "../FundraiserForm/useFundraiserFormLogic.ts";

const EditFundraiser = () => {
  const { slug: currentSlug } = useParams<{ slug: string }>();
  const navigate = useNavigate();

  const {
    fundraiserData,
    isLoadingData,
    updateFundraiser,
    isPending,
    error: updateError,
  } = useEditFundraiser(currentSlug!);
  const formLogic = useFundraiserFormLogic();

  const { setFormData, setRetainedImages } = formLogic;

  useEffect(() => {
    if (fundraiserData) {
      setFormData({
        title: fundraiserData.title,
        slug: fundraiserData.slug,
        description: fundraiserData.description,
        goal: fundraiserData.goal ? String(fundraiserData.goal) : "",
      });
      setRetainedImages(fundraiserData.existingImagesUrls || []);
    }
  }, [fundraiserData, setFormData, setRetainedImages]);

  const handleSubmit = (e: SubmitEvent) => {
    e.preventDefault();
    if (!formLogic.validateForm()) return;

    updateFundraiser(
      {
        title: formLogic.formData.title.trim(),
        slug: formLogic.formData.slug.trim(),
        description: formLogic.formData.description.trim(),
        goal: formLogic.formData.goal
          ? Number(formLogic.formData.goal)
          : undefined,
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
