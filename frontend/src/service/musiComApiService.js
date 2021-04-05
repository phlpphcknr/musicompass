import axios from 'axios'

const baseUrl = '/api'

export const getArtistSearchResult = (artistSearchTerm) =>
  axios
    .get(baseUrl + `/artistsearch/${artistSearchTerm}`)
    .then((response) => response.data)

export const getArtistByName = (artistName) =>
    axios
        .get(baseUrl + `/artist/${artistName}`)
        .then((response) => response.data)

export const getRecommendationTagCategories = () =>
    axios
        .get(baseUrl + `/recommendation`)
        .then((response) => response.data)

export const postRecommendationTag = ({artistName, genreTags, rolesTags, genderTag}) =>
    axios
        .post(baseUrl + `/recommendation`,{artistName, genreTags, rolesTags, genderTag})
        .then((response) => response.data)

export const getRecommendation = ({genreTags, rolesTags, genderTag}) =>
    axios
        .post(baseUrl + `/recommendation/get`,{genreTags, rolesTags, genderTag})
        .then((response) => response.data)