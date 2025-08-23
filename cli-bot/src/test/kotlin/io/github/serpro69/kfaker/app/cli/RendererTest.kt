package io.github.serpro69.kfaker.app.cli

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class RendererTest : DescribeSpec() {

    init {
        describe("Renderer class") {
            val children = List(3) { cId ->
                val grandChildren = List(cId + 1) { gcId ->
                    Renderer("grandchild$gcId", listOf())
                }

                Renderer("child$cId", grandChildren)
            }

            val renderer = Renderer("parent", children)

            it("toString() should return a tree") {
                val tree = """
                    parent
                    ├── child0
                    │   └── grandchild0
                    ├── child1
                    │   ├── grandchild0
                    │   └── grandchild1
                    └── child2
                        ├── grandchild0
                        ├── grandchild1
                        └── grandchild2
                """.trimIndent()

                renderer.toString() shouldBe tree
            }
        }
    }
}
