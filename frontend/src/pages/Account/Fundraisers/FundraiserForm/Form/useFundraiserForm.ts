import { type ChangeEvent, useEffect, useState } from "react";

export type NewImageEntry = { file: File; previewUrl: string };

export const INITIAL_FORM = {
  title: "",
  slug: "",
  description: "",
  goal: "",
};

export const useFundraiserForm = () => {
  const [formData, setFormData] = useState(INITIAL_FORM);
  const [retainedImages, setRetainedImages] = useState<string[]>([]);
  const [newImages, setNewImages] = useState<NewImageEntry[]>([]);
  const [errors, setErrors] = useState<Record<string, string>>({});

  useEffect(() => {
    return () => {
      newImages.forEach((img) => URL.revokeObjectURL(img.previewUrl));
    };
  }, [newImages]);

  const onFieldChange = (e: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setErrors((prev) => ({ ...prev, [name]: "" }));
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const onDescriptionChange = (value?: string) => {
    setErrors((prev) => ({ ...prev, description: "" }));
    setFormData((prev) => ({ ...prev, description: value || "" }));
  };

  const onAddFiles = (files: FileList | null) => {
    if (!files) return;
    const entries = Array.from(files)
      .filter((file) => file.type.startsWith("image/"))
      .map((file) => ({ file, previewUrl: URL.createObjectURL(file) }));
    setNewImages((prev) => [...prev, ...entries]);
    setErrors((prev) => ({ ...prev, images: "" }));
  };

  const onRemoveRetainedImage = (urlToRemove: string) => {
    setRetainedImages((prev) => prev.filter((url) => url !== urlToRemove));
  };

  const onRemoveNewImage = (indexToRemove: number) => {
    setNewImages((prev) => {
      URL.revokeObjectURL(prev[indexToRemove].previewUrl);
      return prev.filter((_, i) => i !== indexToRemove);
    });
  };

  const getTrimmedFormData = () => ({
    ...formData,
    title: formData.title.trim(),
    slug: formData.slug.trim(),
    description: formData.description.trim(),
  });

  const validate = (form: typeof INITIAL_FORM) => {
    const errors: Record<string, string> = {};
    if (!form.title.trim()) errors.title = "Назва обов'язкова";
    if (!form.slug.trim()) errors.slug = "Slug обов'язковий";
    else if (!/^[a-z0-9-]+$/.test(form.slug))
      errors.slug = "Тільки малі латинські літери, цифри та дефіс";
    if (!form.description.trim()) errors.description = "Опис обов'язковий";
    if (form.goal && Number(form.goal) <= 0)
      errors.goal = "Ціль має бути більшою за 0";
    return errors;
  };

  const validateForm = () => {
    const trimmed = getTrimmedFormData();
    const validationErrors = validate(trimmed);

    if (retainedImages.length === 0 && newImages.length === 0) {
      validationErrors.images = "Хоча б одне зображення обов'язкове";
    }

    if (Object.keys(validationErrors).length) {
      setErrors(validationErrors);
      return null;
    }
    return trimmed;
  };

  return {
    formData,
    errors,
    retainedImages,
    newImages,
    onFieldChange,
    onDescriptionChange,
    onAddFiles,
    onRemoveRetainedImage,
    onRemoveNewImage,
    setFormData,
    setRetainedImages,
    validateForm,
  };
};
