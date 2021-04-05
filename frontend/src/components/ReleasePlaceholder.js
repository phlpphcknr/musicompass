import styled from 'styled-components/macro'

export default function ReleasePlaceholder({releasetype}){

    return (
        <Release>
            <h3>Unfortunately, there are no {releasetype} to be displayed</h3>
        </Release>
    )
}



const Release = styled.section`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px;
  margin: 0px 20px 20px 20px;
  background-color: var(--primary-color);
  box-shadow: 0px 0px 6px #333;
  
  section {
    width: 100%;
    text-align: center;
    color: #636e72;
  }
  
`