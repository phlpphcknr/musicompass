import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  * {
    box-sizing: border-box;
  }

  html, body {
    margin: 0;
    font-size: 112.5%;
    font-family: "Courier New";
  }
  :root{
    --primary-color: #fdcb6e
  }
`