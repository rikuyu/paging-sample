package com.example.pagingsample

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingsample.model.data.Character
import com.example.pagingsample.repository.MainRepository

class CharacterPagingSource(
    private val repo: MainRepository
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        try {
            val pageKey = params.key ?: 1
            val response = repo.getCharacter(pageKey)

            val characters = response.body()

            return if (characters != null) {
                val nextPageKey = pageKey + 1
                val prevPageKey = pageKey + 1

                LoadResult.Page(
                    data = characters.results,
                    prevKey = prevPageKey,
                    nextKey = nextPageKey
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}