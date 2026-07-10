import Foundation
import PDFKit
import UIKit

@objc public class PdfViewerViewController: UIViewController {
    var onClosed: ((PdfViewerViewController) -> Void)?
    var onPageChanged: ((Int) -> Void)?

    private let document: PDFDocument
    private let initialPage: Int
    private var lastPage: Int
    private var pdfView: PDFView?

    init(document: PDFDocument, title: String?, page: Int) {
        self.document = document
        self.initialPage = page
        self.lastPage = page
        super.init(nibName: nil, bundle: nil)
        self.title = title
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    deinit {
        NotificationCenter.default.removeObserver(self)
    }

    override public func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        if isBeingDismissed || navigationController?.isBeingDismissed == true {
            onClosed?(self)
        }
    }

    override public func viewDidLoad() {
        super.viewDidLoad()
        view.backgroundColor = .systemBackground
        configureNavigationBar()
        let pdfView = createPdfView()
        self.pdfView = pdfView
        view.addSubview(pdfView)
        NSLayoutConstraint.activate([
            pdfView.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
            pdfView.leadingAnchor.constraint(equalTo: view.leadingAnchor),
            pdfView.trailingAnchor.constraint(equalTo: view.trailingAnchor),
            pdfView.bottomAnchor.constraint(equalTo: view.bottomAnchor)
        ])
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(handlePageChanged),
            name: Notification.Name.PDFViewPageChanged,
            object: pdfView
        )
        goToInitialPage()
    }

    private func configureNavigationBar() {
        navigationItem.rightBarButtonItem = UIBarButtonItem(
            barButtonSystemItem: .done,
            target: self,
            action: #selector(handleDone)
        )
    }

    private func createPdfView() -> PDFView {
        let pdfView = PDFView()
        pdfView.translatesAutoresizingMaskIntoConstraints = false
        pdfView.autoScales = true
        pdfView.displayMode = .singlePageContinuous
        pdfView.displayDirection = .vertical
        pdfView.document = document
        return pdfView
    }

    private func goToInitialPage() {
        guard initialPage > 1, let page = document.page(at: initialPage - 1) else {
            return
        }
        pdfView?.go(to: page)
    }

    @objc private func handleDone() {
        dismiss(animated: true)
    }

    @objc private func handlePageChanged() {
        guard let pdfView = pdfView, let currentPage = pdfView.currentPage else {
            return
        }
        let page = document.index(for: currentPage) + 1
        if page == lastPage {
            return
        }
        lastPage = page
        onPageChanged?(page)
    }
}
