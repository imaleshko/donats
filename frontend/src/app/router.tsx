import { createBrowserRouter, redirect } from "react-router";
import { Layout } from "@/layout/Layout/Layout";
import { Home } from "@/pages/Home/Home";
import { queryClient } from "./queryClient.ts";
import { fundraisersApi } from "@/api/fundraisersApi.ts";
import Register from "@/pages/Auth/Register/Register.tsx";
import Login from "@/pages/Auth/Login/Login.tsx";
import Account from "@/pages/Account/Account.tsx";
import Profile from "@/pages/Account/Profile/Profile.tsx";
import { accountApi } from "@/api/accountApi.ts";
import { requireAuth } from "@/app/requireAuth.ts";
import CreateFundraiser from "../pages/Account/Fundraisers/CreateFundraiser/CreateFundraiser.tsx";
import Donations from "@/pages/Account/Donations/Donations.tsx";
import EditFundraiser from "../pages/Account/Fundraisers/EditFundraiser/EditFundraiser.tsx";
import AddUpdate from "../pages/Account/Fundraisers/FundraiserUpdate/AddUpdate.tsx";
import { Fundraiser } from "@/pages/Fundraiser/Fundraiser.tsx";
import Fundraisers from "@/pages/Account/Fundraisers/Fundraisers.tsx";

export const router = createBrowserRouter([
  {
    path: "/",
    Component: Layout,
    loader: async () => {
      try {
        return await queryClient.ensureQueryData({
          queryKey: ["user"],
          queryFn: accountApi.getUser,
        });
      } catch {
        queryClient.setQueryData(["user"], null);
        return null;
      }
    },

    children: [
      {
        index: true,
        Component: Home,
        loader: async () => {
          return await queryClient.ensureQueryData({
            queryKey: ["newest"],
            queryFn: fundraisersApi.getNewest,
          });
        },
      },
      {
        path: "fundraiser/:username/:slug",
        Component: Fundraiser,
        errorElement: (
          <div style={{ padding: "40px", color: "white", textAlign: "center" }}>
            Збір не знайдено (404)
          </div>
        ),
        loader: async ({ params }) => {
          const { username, slug } = params;

          if (!username || !slug) {
            throw redirect("/");
          }
          return await queryClient.ensureQueryData({
            queryKey: ["fundraiser", username, slug],
            queryFn: () => fundraisersApi.getByUsernameAndSlug(username, slug),
          });
        },
      },
      {
        path: "register",
        Component: Register,
      },
      {
        path: "login",
        Component: Login,
      },
      {
        path: "account",
        Component: Account,
        loader: requireAuth,
        children: [
          {
            index: true,
            loader: () => redirect("profile"),
          },
          {
            path: "profile",
            Component: Profile,
          },
          {
            path: "donations",
            Component: Donations,
          },
          {
            path: "fundraisers",
            children: [
              {
                index: true,
                Component: Fundraisers,
              },
              {
                path: "create",
                Component: CreateFundraiser,
              },
              {
                path: "edit/:slug",
                Component: EditFundraiser,
              },
              {
                path: "add-update/:id",
                Component: AddUpdate,
              },
            ],
          },
        ],
      },
    ],
  },
]);
