import { createGlobalStyle } from 'styled-components'

export default createGlobalStyle`
  html, body, #root {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    height: 100%;
    font-family: "Courier New";
    background-color: var(--seondary-color);
    --primary-color: #fdcb6e;
    --secondary-color: moccasin;
    --opacity-hover: 0.7;
  }
  
  section{
    padding: 0px;
    margin: 0px;
  }
  
  img{
    max-width: 120px;
    max-height: 120px;
    width: auto;
    height: auto;
    margin: 10px;
  }
  
  p{
    font-weight: bold;
  }

  h5 {
    margin: 0;
    padding: 0;
  }

  a {
    text-decoration: none;
    color: chocolate;

    :hover {
      opacity: var(--opacity-hover);
    }
  }

  select {
    font-family: "Courier New";
  }

`
