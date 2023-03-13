# RecyclerView Infinite Scrioll
The RecyclerView is a widely used component in Android development for displaying large datasets efficiently. It can handle large lists by recycling and reusing item views, improving the performance of your app.

Infinite scrolling is a technique that allows users to scroll through an endless list of items, loading more items as the user reaches the end of the current list. This feature is especially useful when dealing with large datasets that are too large to display at once.

# Preview:
https://user-images.githubusercontent.com/50836835/224614008-a2ba50dd-9176-4c5e-b3f0-15c476db1dee.mp4

# How to Install
> Step 1. Add the JitPack repository to your build file
  * Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
```
  
> Step 2. Add the dependency
```
dependencies {
	  implementation 'com.github.DevenDeveloper:RecyclerView_Infinite_Scrioll:1.0.0'
}
```

# Example
```kotlin
 private fun onSetRecyclerView() {
        val images: MutableList<ModelImages> = ArrayList()
        images.add(ModelImages(1, "", "https://images.idgesg.net/images/article/2019/05/cso_best_security_software_best_ideas_best_technology_lightbulb_on_horizon_of_circuit_board_landscape_with_abstract_digital_connective_technology_atmosphere_ideas_innovation_creativity_by_peshkov_gettyimages-965785212_3x2_2400x1600-100797318-large.jpg?auto=webp&quality=85,70"))
        images.add(ModelImages(2, "", "https://imageio.forbes.com/specials-images/imageserve/637b1d11729a96ce28ea598c/The-Top-10-Tech-Trends-In-2023-Everyone-Must-Be-Ready-For/960x0.jpg?format=jpg&width=960"))
        images.add(ModelImages(3, "", "https://s35764.pcdn.co/wp-content/uploads/2021/09/tech2-1024x630.png"))
        images.add(ModelImages(4, "", "https://investormediapro.bg/wp-content/uploads/2021/02/Tech-of-tomorrow-tint-1130x636-1.jpg"))
        images.add(ModelImages(4, "", "https://www.valueresearchonline.com/content-assets/images/50971_20220623-tech__w660__.jpg"))
        val layoutManager1: RecyclerView.LayoutManager = CenterZoomLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false
        )

        val layoutManager = InfiniteLayoutManager()
        layoutManager.setInfiniteEnable(true)
        binding.recyclerView.scrollToPosition(images.size / 2)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ImageAdapter(this, images)
    }
    ```
