/**
 * Represents an author with a unique identifier and username.
 *
 * @remarks
 * This interface is used for data transfer (DTO) when working with posts,
 * comments, or any feature that needs author info.
 *
 * @property id – The unique numeric identifier of the author.
 * @property username – The display name or handle of the author used sent by api to avoid a second api call to get the autor name
 *
 */
export interface author {
    "id": number,
    "username": string
}