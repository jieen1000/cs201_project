import { makeStyles } from "@mui/styles";
import bca from "assets/images/lsbanner.jpg";

/**
 * {@code MarketplaceBanner} creates the component for labour sharing marketplace banner.
 *
 * @author Pang Jun Rong
 * @version 1.0
 * @since 2021-10-16
 */

export default makeStyles(({ functions, palette, borders }) => {
  const { linearGradient, rgba, pxToRem } = functions;
  const { gradients } = palette;
  const { borderRadius } = borders;

  return {
    marketplaceBanner_content: {
      backgroundImage: `${linearGradient(
        rgba(gradients.dark.main, 0.8),
        rgba(gradients.dark.state, 0.8)
      )}, url(${bca})`,
      backgroundSize: "cover",
      backgroundPosition: "30% 30%",
      display: "flex",
      flexDirection: "column",
      height: "100%",
      padding: pxToRem(16),
      borderRadius: borderRadius.lg,
    },

    marketplaceBanner_button: {
      marginTop: "auto",
      marginRight: "auto",
      display: "inline-flex",
      alignItems: "center",
      cursor: "pointer",

      "& .material-icons-round": {
        fontSize: "1.125rem",
        transform: `translate(${pxToRem(4)}, ${pxToRem(0)})`,
        transition: "transform 0.2s cubic-bezier(0.34,1.61,0.7,1.3)",
      },

      "&:hover .material-icons-round, &:focus .material-icons-round": {
        transform: `translate(${pxToRem(10)}, ${pxToRem(-10)})`,
      },
    },
  };
});
